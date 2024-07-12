<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\SchoolController;
use App\Http\Controllers\RepresentativeController;
use Illuminate\Support\Facades\Auth; 
use App\Http\Controllers\ChallengeController;
use App\Http\Controllers\QuestionController;
use App\Http\Controllers\AnswerController;
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

Route::resource('schools', SchoolController::class);
Route::resource('representatives', RepresentativeController::class);

Route::get('/schools', [SchoolController::class, 'index']);
Route::post('/schools', [SchoolController::class, 'store']);


Route::get('/representatives', [RepresentativeController::class, 'index']);
//Route::post('/representatives', [RepresentativeController::class, 'store']);
//Route::delete('/representatives/{id}', [RepresentativeController::class, 'destroy']);

Route::delete('/schools/{id}', [SchoolController::class, 'destroy'])->name('schools.destroy');
Route::delete('/representatives/{id}', [RepresentativeController::class, 'destroy'])->name('representatives.destroy');

Route::post('/representatives', [RepresentativeController::class, 'store']);

Route::get('/', function () {
    return view('welcome');
});

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






Route::get('/challenges', [ChallengeController::class, 'index'])->name('challenges.index');
Route::post('/challenges/store', [ChallengeController::class, 'store'])->name('challenges.store');

Route::get('/question/import', [App\Http\Controllers\QuestionController::class, 'index']);
Route::post('/import', [App\Http\Controllers\QuestionController::class, 'import']);

Route::get('/answer/upload', [App\Http\Controllers\AnswerController::class, 'index']);
Route::post('/upload', [App\Http\Controllers\AnswerController::class, 'upload'])->name('upload');

