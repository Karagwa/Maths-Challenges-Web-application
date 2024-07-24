<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\School;

class SchoolController extends Controller
{


   public function index()
   {
    
       try {
           $schools = School::all();
           return view('pages.table', compact('schools'));
       } catch (\Exception $e) {
           return response()->json(['error' => 'Failed to fetch schools: ' . $e->getMessage()], 500);
       }
   
   }
    public function fetchSchools()
    {
        $schools = School::all();
        
        // Convert the data to a custom delimited string
        $output = "";
        foreach ($schools as $school) {
            $output .= $school->id . "|" . $school->name . "|" . $school->district . "|" . $school->regno . "\n";
        }

        return response(rtrim($output), 200)->header('Content-Type', 'text/plain');
    }

    public function addSchool(Request $request)
    {
        $school = new School();
        
        $school->name = $request->name;
        $school->district = $request->district;
        $school->regno = $request->regno;
        $school->save();

        return response("success|School added successfully", 200)->header('Content-Type', 'text/plain');
    }

    public function edit($id)
    {
        $school = School::find($id); // Use find to get the school by its primary key
        if (!$school) {
            return redirect()->route('school.index')->with('error', 'School not found');
        }
        return view('pages.editing', compact('school')); // Use compact to pass the school variable
    }
    
    public function update(Request $request, $id)
    {
        try {
            $school = School::find($id);
            if (!$school) {
                return redirect()->route('school.index')->with('error', 'School not found');
            }
    
            $school->update($request->all());
    
            return redirect()->route('school.index')->with('success', 'School updated successfully');
        } catch (\Exception $e) {
            return redirect()->route('school.index')->with('error', 'Failed to update school: ' . $e->getMessage());
        }
    }

    public function destroy($id)
    {
        try {
            $school = School::find($id);
            if (!$school) {
                return redirect()->route('school.index')->with('error', 'School not found');
            }
            $school->delete();
            return redirect()->route('school.index')->with('success', 'School deleted successfully');
        } catch (\Exception $e) {
            return redirect()->route('school.index')->with('error', 'Failed to delete school: ' . $e->getMessage());
        }
    }
}

?>
