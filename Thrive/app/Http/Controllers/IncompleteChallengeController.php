<?php

namespace App\Http\Controllers;
use App\Models\IncompleteChallenge;
use App\Models\Repetition;
use Illuminate\Support\Facades\Log;


use Illuminate\Http\Request;

class IncompleteChallengeController extends Controller
{
    public function index(){
        return view('pages.IncompleteChallenges');
    }
    public function fetchIncompleteChallenges()
{
    try {
        $Challenges = IncompleteChallenge::all();
        $output = "";
        foreach ($Challenges as $Challenge) {
            // Debugging output
            Log::info('Challenge data: ', $Challenge->toArray());
            
            $output .= $Challenge->username . "|" . $Challenge->Firstname . "|" . $Challenge->Lastname . "|" . $Challenge->ChallengeNumber . "\n";
        }
        return response(rtrim($output), 200)->header('Content-Type', 'text/plain');
    } catch (\Exception $e) {
        Log::error('Failed to fetch incomplete challenges: ' . $e->getMessage());
        return response('error|Failed to fetch incomplete challenges: ' . $e->getMessage(), 500)->header('Content-Type', 'text/plain');
    }
}


    public function fetchRepetitionPercentages()
    {
        try {
            $repetitions = Repetition::all();
            $output = "";
            foreach ($repetitions as $repetition) {
                $output .= $repetition->repetition_percentage . "|" . $repetition->challenge_number . "\n"; // Ensure attribute names are correct
            }
            return response(rtrim($output), 200)->header('Content-Type', 'text/plain');
        } catch (\Exception $e) {
            return response('error|Failed to fetch repetitions: ' . $e->getMessage(), 500)->header('Content-Type', 'text/plain');
        }
    }
    
}
