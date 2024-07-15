<?php
namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Representative;

class RepresentativeController extends Controller
{
    /**
     * Display a listing of the representatives.
     *
     * @return \Illuminate\Http\Response
     */
    public function fetchRepresentatives()
    {
        try {
            $representatives = Representative::all();
            $output = "";
            foreach ($representatives as $representative) {
                $output .= $representative->id . "|" . $representative->username . "|" . $representative->name . "|" . $representative->email . "|" . $representative->regno . "\n";
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

    /**
     * Remove the specified representative from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
{
    try {
        $representative = Representative::findOrFail($id);
        $representative->delete();
        return response('success|Representative deleted successfully', 200)->header('Content-Type', 'text/plain');
    } catch (\Exception $e) {
        return response('error|Failed to delete representative: ' . $e->getMessage(), 500)->header('Content-Type', 'text/plain');
    }
}

}
?>