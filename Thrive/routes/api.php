<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\SchoolController;
use App\Http\Controllers\RepresentativeController;
use App\Http\Controllers\AnswerController;
use App\Http\Controllers\QuestionController;
 // Add this line to import AnswerController

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/
Route::post('/schools', [SchoolController::class, 'store']);
Route::post('/representatives', [RepresentativeController::class, 'store']);
Route::delete('/schools/{id}', [SchoolController::class, 'destroy'])->name('schools.destroy');
Route::delete('/representatives/{id}', [RepresentativeController::class, 'destroy'])->name('representatives.destroy');
Route::post('/import', [QuestionController::class, 'import']);
Route::post('/import', [AnswerController::class, 'import']);
Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
