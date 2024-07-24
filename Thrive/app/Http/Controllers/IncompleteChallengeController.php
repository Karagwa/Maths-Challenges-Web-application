<?php

namespace App\Http\Controllers;
use App\Models\IncompleteChallenge;

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
                $output .= $Challenge->username . "|" . $Challenge->Firstname . "|" . $Challenge->Lastname . "|" . $Challenges->ChallengeNumber."\n";
            }
            return response(rtrim($output), 200)->header('Content-Type', 'text/plain');
        } catch (\Exception $e) {
            return response('error|Failed to fetch representatives: ' . $e->getMessage(), 500)->header('Content-Type', 'text/plain');
        }
    }
}
