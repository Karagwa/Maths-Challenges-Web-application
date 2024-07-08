<?php

/*namespace App\Http\Controllers;

use App\Models\Representative;
use Illuminate\Http\Request;

class RepresentativeController extends Controller
{
    public function index()
    {
        $representatives = Representative::all();
        return response()->json($representatives);
    }

    public function store(Request $request)
    {
       $validateddata = $request->validate([
            'name' => 'required|string|max:255',
            'email' => 'required|email|unique:representatives',
        ]);
        \Log::info($request->all());  // Log the request data
        try {
            $representative = Representative::create($validateddata);
            return response()->json(['success' => true, 'representative' => $representative]);
        } catch (\Exception $e) {
            return response()->json(['success' => false, 'message' => $e->getMessage()]);
        }

       // $representative = Representative::create($request->all());
       // return response()->json(['success' => 'Representative added successfully', 'representative' => $representative]);
    }

    
    public function destroy($id)
{
    $representative = Representative::findOrFail($id);
    $representative->delete();

    return response()->json(['success' => true]);
}
}*/
namespace App\Http\Controllers;

use App\Models\Representative;
use Illuminate\Http\Request;

class RepresentativeController extends Controller
{
    public function index()
    {
        $representatives = Representative::all();
        return response()->json($representatives);
    }

        public function store(Request $request)
        {
            $validator = Validator::make($request->all(), [
                'name' => 'required|string|max:255',
                'email' => 'required|email|max:255'
            ]);
        
            if ($validator->fails()) {
                return response()->json(['success' => false, 'errors' => $validator->errors()], 400);
            }
        
            try {
                $representative = Representative::create([
                    'name' => $request->input('name'),
                    'email' => $request->input('email')
                ]);
        
                return response()->json(['success' => true, 'representative' => $representative]);
            } catch (\Exception $e) {
                return response()->json(['success' => false, 'error' => $e->getMessage()], 500);
            }
        
        

        }

    public function destroy(Representative $representative)
    {
        $representative->delete();
        return response()->json(['success' => true]);
    }
}
