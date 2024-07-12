<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Maatwebsite\Excel\Facades\Excel;
use App\Imports\AnswersImport;


class AnswerController extends Controller
{
    public function index(){
        return view('pages.challenge');
    }

    public function upload(Request $request){
        $request->validate([
            'import_answers' => ['required','mimes:xlsx,xls','max:10240'],
        ]);
        

        try{
            Excel::import(new AnswersImport, $request->file('import_answers'));
            return redirect()->back()->with('success', 'Answers imported successfully.');
        }catch(\Exception $e){
            error_log($e->getMessage());
        
           // dd($request->all());
            return redirect()->back()->with('success', 'Answers imported successfully.');
    }

    dd($request->all());
}
}
