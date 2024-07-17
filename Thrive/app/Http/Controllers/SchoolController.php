<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\School;

class SchoolController extends Controller
{
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

    
    public function delete($id)
    {
     try {
        $school = School::findOrFail($id);
        $school->delete();

        return response("success|School deleted successfully", 200)->header('Content-Type', 'text/plain');
    } catch (\Exception $e) {
        return response("error|Failed to delete school: " . $e->getMessage(), 500)->header('Content-Type', 'text/plain');
    }
    }
}

?>
