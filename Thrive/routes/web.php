<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\SchoolController;
use App\Http\Controllers\RepresentativeController;
use App\Http\Controllers\IncompleteChallengeController;
use Illuminate\Support\Facades\Auth;
use App\Http\Controllers\ChallengeController;
use App\Http\Controllers\ParticipantController;
use App\Http\Controllers\RankingController;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/
 //Route::post('/delete-school', 'SchoolController@deleteSchool')->name('delete.school');
//Route::post('/delete-representative', 'RepresentativeController@deleteRepresentative')->name('delete.representative');


Route::get('/schools', [SchoolController::class, 'index'])->name('school.index');
Route::get('/fetch_schools', [SchoolController::class, 'fetchSchools']);
Route::post('/add_school', [SchoolController::class, 'addSchool']);

Route::post('/school/{id}', [SchoolController::class, 'update']);

Route::delete('/school/{id}', [SchoolController::class, 'destroy'])->name('school.destroy');
   



Route::get('/representatives', [RepresentativeController::class, 'index'])->name('representative.index');
Route::get('/fetch_representatives', [RepresentativeController::class, 'fetchRepresentatives']);
Route::post('/add_representative', [RepresentativeController::class, 'addRepresentative']);
Route::delete('/representative/{id}', [RepresentativeController::class, 'destroy'])->name('representative.destroy');
Route::post('/representative/{id}', [RepresentativeController::class, 'update']);



Route::get('IncompleteChallenges', [IncompleteChallengeController::class,'index'])->name('IncompleteChallenges');
Route::get('/fetch_Incomplete_challenges', [IncompleteChallengeController::class, 'fetchIncompleteChallenges'])-> name('fetch_Incomplete_challenges');

//school rankings routes
Route::get('/rankings', [ChallengeController::class, 'showYearlyChallengeResults'])->name('rankings');
Route::get('/fetch_rankings', [RankingController::class, 'fetchRankings']);


//question analytics routes
Route::get('/questionanalytics', [ChallengeController::class, 'showQuestionAnaltics'])->name('questions');







Route::get('/', function () {
    return view('welcome');
});

//best 2 participants routes
Route::get('/best-students', [ParticipantController::class, 'index'])->name("best_two");



Auth::routes();

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');
Auth::routes();

Route::get('/home', 'App\Http\Controllers\HomeController@index')->name('dashboard');

Route::group(['middleware' => 'auth'], function () {
	Route::resource('user', 'App\Http\Controllers\UserController', ['except' => ['show']]);
	Route::get('profile', ['as' => 'profile.edit', 'uses' => 'App\Http\Controllers\ProfileController@edit']);
	Route::patch('profile', ['as' => 'profile.update', 'uses' => 'App\Http\Controllers\ProfileController@update']);
	Route::patch('profile/password', ['as' => 'profile.password', 'uses' => 'App\Http\Controllers\ProfileController@password']);
});


Route::group(['middleware' => 'auth'], function () {
	Route::get('{page}', ['as' => 'page.index', 'uses' => 'App\Http\Controllers\PageController@index']);
});


//Challenge routes
Route::get('/challenges', [ChallengeController::class, 'index'])->name('challenges.index');
Route::post('/challenges/store', [ChallengeController::class, 'store'])->name('challenges.store');

//question routes
Route::get('/question/import', [App\Http\Controllers\QuestionController::class, 'index']);
Route::post('/import', [App\Http\Controllers\QuestionController::class, 'import']);

//answer routes
Route::get('/answer/upload', [App\Http\Controllers\AnswerController::class, 'index']);
Route::post('/upload', [App\Http\Controllers\AnswerController::class, 'upload'])->name('upload');