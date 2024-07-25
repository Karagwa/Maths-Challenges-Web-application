<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Representative;
use Illuminate\Support\Facades\Log;

class RepresentativeController extends Controller
{
    

     public function index()
     {
         try {
             $representatives = Representative::all();
             return view('pages.table2', compact('representatives'));
         } catch (\Exception $e) {
             return response()->json(['error' => 'Failed to fetch representatives: ' . $e->getMessage()], 500);
         }
     }
    public function fetchRepresentatives()
    {
        try {
            $representatives = Representative::all();
            $output = "";
            foreach ($representatives as $representative) {
                $output .= $representative->id . "|" . $representative->username . "|" . $representative->name . "|" . $representative->email . "|" . $representative->regno . "|" . $representative->password. "\n";
            }
            return response(rtrim($output), 200)->header('Content-Type', 'text/plain');
        } catch (\Exception $e) {
            return response('error|Failed to fetch representatives: ' . $e->getMessage(), 500)->header('Content-Type', 'text/plain');
        }
    }
    /**
     * Store a newly created representative in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function addRepresentative(Request $request)
    {
        try {
            // Validate input data (example)
            $validatedData = $request->validate([
                'username' => 'required|string|max:255',
                'name2' => 'required|string|max:255',
                'email' => 'required|email|unique:representatives,email',
                'regno' => 'required|string|max:255',
                'password' => 'required|string|max:255',
            ]);

            // Creates new representative instance
            $representative = new Representative();
            $representative->username = $validatedData['username'];
            $representative->name = $validatedData['name2'];
            $representative->email = $validatedData['email'];
            $representative->regno = $validatedData['regno'];
            $representative->password = $validatedData['password'];
            $representative->save();

            // Return success response
            return response('success|Representative added successfully', 200)->header('Content-Type', 'text/plain');
        } catch (\Exception $e) {
            // Return error response if an exception occurs
            return response('error|Failed to add representative: ' . $e->getMessage(), 500)->header('Content-Type', 'text/plain');
        }
    }

    
 
     public function edit($id)
{
    
   $representative = Representative::where('id', $id)->firstOrFail();
     
    
  if (!$representative) {
    return redirect()->route('representative.index')->with('error', 'rep not found');
  }
  return view('pages.editing2', compact('representative'));
}

     public function update(Request $request, $id)
     {
         try {
             $representative = Representative::find($id);
             if (! $representative) {
                 return redirect()->route('representative.index')->with('error', 'representative not found');
             }
     
             $representative ->update($request->all());
     
             return redirect()->route('representative.index')->with('success', 'representative updated successfully');
         } catch (\Exception $e) {
             return redirect()->route('representative.index')->with('error', 'Failed to update representative: ' . $e->getMessage());
         }
     }
     public function destroy($id)
    {
        Log::info("Attempting to delete representative with username: {$id}");
        
     

    try {
        $representative= Representative::find($id);
        if (!$representative) {
            return response("error|Rep not found", 404)->header('Content-Type', 'text/plain');
        }
        $representative->delete();
        return response("success|Representative deleted successfully", 200)->header('Content-Type', 'text/plain');
    } catch (\Exception $e) {
        return response("error|Failed to delete representative: " . $e->getMessage(), 500)->header('Content-Type', 'text/plain');
    }
    
    
    
}
} 
    

?>