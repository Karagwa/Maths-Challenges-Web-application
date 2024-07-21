<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Mark;
use Carbon\Carbon;

class ParticipantController extends Controller
{
    //
    public function index()
    {
        // Get the current date
        $currentDate = Carbon::now();

        // Get all challenges that have ended
        $challenges = Mark::with(['participant', 'challenge'])
            ->whereHas('challenge', function ($query) use ($currentDate) {
                $query->where('ClosingDate', '<', $currentDate);
            })
            ->get()
            ->groupBy('ChallengeNumber')
            ->map(function ($marks) {
                // Get the top two pupils for each challenge
                return $marks->sortByDesc('TotalScore')->take(2)->map(function ($mark) {
                    return [
                        'challenge_name' => $mark->challenge->ChallengeName,
                        'challenge_end_date' => $mark->challenge->ClosingDate,
                        'name' => $mark->participant->Firstname,
                        //'image' => $mark->student->image,
                        'image' => $mark->participant->Image ? base64_encode($mark->participant->Image) : null,
                        'school' => $mark->participant->regno,
                        'marks' => $mark->TotalScore
                    ];
                });
            });

        return view('pages.best_students', compact('challenges'));
    }
}
