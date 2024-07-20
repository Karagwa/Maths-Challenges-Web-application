<?php

namespace App\Http\Controllers;

use App\Imports\QuestionImport;
use Illuminate\Http\Request;
use Maatwebsite\Excel\Facades\Excel;


use App\Imports\QuestionsImport; // Add this line to import the QuestionsImport class

class QuestionController extends Controller
{
    public function index(){
        return view('pages.challenge');
    }
    public function import(Request $request){
        $request->validate([
            'import_questions' =>['required','mimes:xlsx,xls','max:10240'],
                
        ]);

        try{
        Excel::import(new QuestionsImport, $request->file('import_questions'));
        $message = 'Questions imported successfully.';
        } catch(\Exception $e){
            return redirect()->back()->with('error', 'Error importing questions: '.$e->getMessage());
        }

        return redirect()->back()->with('success', 'Questions imported successfully.', $message);
    }

    
}