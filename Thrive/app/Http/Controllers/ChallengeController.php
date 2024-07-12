<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Challenge;


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
        'ChallengeNo' => 'required|integer',
        'ChallengeName' => 'required|string',
        'OpeningDate' => 'required|date',
        'ClosingDate' => 'required|date',
        'ChallengeDuration' => 'required|integer',
        'NumberOfPresentedQuestions' => 'required|integer'
        // Validate other fields as necessary
    ]);

    // $challenge = new Challenge();
    // $challenge->ChallengeNo = $request->ChallengeNo;
    // $challenge->ChallengeName = $request->ChallengeName;
    // $challenge->OpeningDate = $request->OpeningDate;
    // $challenge->ClosingDate = $request->ClosingDate;
    // $challenge->ChallengeDuration = $request->ChallengeDuration;
    // $challenge->NumberOfPresentedQuestions = $request->NumberOfPresentedQuestions;
    // // Assign other fields as necessary

    
    $challenge = Challenge::create($request->all()); // Use create method of the model

    if ($challenge ) {
      return back()->with('success', 'Challenge saved successfully.');
    } else {
      return back()->with('error', 'Failed to save challenge.');
    }
    
    
        
    
}}
