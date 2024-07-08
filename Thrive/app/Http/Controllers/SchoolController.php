<?php

namespace App\Http\Controllers;

use App\Models\School;
use Illuminate\Http\Request;

class SchoolController extends Controller
{
    public function index()
    {
        return response()->json(School::all());
    }

    public function store(Request $request)
    {
        $request->validate([
            'name' => 'required',
            'district' => 'required',
            'regno' => 'required'
        ]);
        \Log::info($request->all());  // Log the request data
        try {
            $school = School::create($request->all());
            return response()->json(['success' => true, 'school' => $school]);
        } catch (\Exception $e) {
            return response()->json(['success' => false, 'message' => $e->getMessage()]);
        }

        
       // $school = School::create($request->all());
       // return response()->json(['success' => 'School added successfully', 'school' => $school]);
    }

   
    public function destroy($id)
{
    $school = School::findOrFail($id);
    $school->delete();
    return redirect()->back();

    return response()->json(['success' => true]);
}



}

