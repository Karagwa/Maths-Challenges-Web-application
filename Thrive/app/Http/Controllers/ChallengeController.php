<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Challenge;
use App\Models\Mark;
use Illuminate\Support\Facades\DB;
use Carbon\Carbon;


class ChallengeController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return Challenge::all();
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        return view('challenges.create');
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    
    public function store(Request $request)
    {
    $request->validate([
        'ChallengeNumber' => 'required|integer',
        'ChallengeName' => 'required|string',
        'OpeningDate' => 'required|date',
        'ClosingDate' => 'required|date',
        'ChallengeDuration' => 'required|integer',
        'NumberOfPresentedQuestions' => 'required|integer'
        // Validate other fields as necessary
    ]);

    

    
    $challenge = Challenge::create($request->all()); // Use create method of the model

    if ($challenge ) {
      return back()->with('success', 'Challenge saved successfully.');
    } else {
      return back()->with('error', 'Failed to save challenge.');
    }
    
    
        
    
    }

    public function showYearlyChallengeResults()
    {
        // Get the current year
        $currentYear = Carbon::now()->year;

        // Fetch closed challenges for the current year
        $closedChallenges = Challenge::whereYear('ClosingDate', $currentYear)
            ->where('ClosingDate', '<', Carbon::now())
            ->pluck('ChallengeNumber');

        // Calculate average marks per school for the closed challenges of the current year
        $schoolsAvgMarks = Mark::select('regno', DB::raw('AVG(TotalScore) as avg_marks'))
            ->whereIn('ChallengeNumber', $closedChallenges)
            ->groupBy('regno')
            ->with('school')
            ->get();

        // Sort and rank all schools
        $rankedSchools = $schoolsAvgMarks->sortByDesc('avg_marks');

        // Get top 10 and bottom 10 schools
        $top10Schools = $rankedSchools->take(10);
        $bottom10Schools = $rankedSchools->reverse()->take(10);

        // Calculate worst performing schools for each ended challenge
        $worstSchoolsPerChallenge = Challenge::whereIn('ChallengeNumber', $closedChallenges)
            ->with(['marks' => function ($query) {
                $query->with('school')
                    ->select('ChallengeNumber', 'regno', DB::raw('AVG(TotalScore) as avg_marks'))
                    ->groupBy('ChallengeNumber', 'regno')
                    ->orderBy('avg_marks', 'asc')
                    ->take(5);
            }])
            ->get();

        return view('pages.rankings', compact('rankedSchools', 'top10Schools', 'bottom10Schools', 'worstSchoolsPerChallenge'));
    }
}
