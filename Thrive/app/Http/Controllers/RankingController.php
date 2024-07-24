<?php

namespace App\Http\Controllers;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

class RankingController extends Controller
{
    public function fetchRankings()
    {
        // Fetch distinct years
        $years = DB::table('school_rankings')->distinct()->pluck('year');

        // Fetch distinct school names
        $schools = DB::table('school_rankings')->distinct()->pluck('school');

        // Initialize an array to hold rankings
        $rankings = [];

        // Loop through each school and fetch their rankings
        foreach ($schools as $school) {
            $schoolRankings = DB::table('school_rankings')
                ->where('school', $school)
                ->orderBy('year')
                ->pluck('ranking');
            $rankings[$school] = $schoolRankings;
        }

        // Return the data as JSON
        return response()->json([
            'years' => $years,
            'schools' => $schools,
            'rankings' => $rankings,
        ]);
    }
}
