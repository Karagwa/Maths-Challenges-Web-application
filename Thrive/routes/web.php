<?php

use Illuminate\Support\Facades\Route;
use App\Http\Controllers\SchoolController;
use App\Http\Controllers\RepresentativeController;
use App\Http\Controllers\IncompleteChallengeController;
use Illuminate\Support\Facades\Auth;

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


Route::get('/fetch_schools', [SchoolController::class, 'fetchSchools']);
Route::post('/add_school', [SchoolController::class, 'addSchool']);
Route::delete('/schools/{id}', [SchoolController::class, 'delete'])->name('schools.delete');

Route::get('/fetch_representatives', [RepresentativeController::class, 'fetchRepresentatives']);
Route::post('/add_representative', [RepresentativeController::class, 'addRepresentative']);
Route::delete('/representatives/{id}', [RepresentativeController::class, 'destroy'])->name('representatives.delete');
Route::get('IncompleteChallenges', [IncompleteChallengeController::class,'index'])->name('IncompleteChallenges');
Route::get('/fetch_Incomplete_challenges', [IncompleteChallengeController::class, 'fetchIncompleteChallenges'])-> name('fetch_Incomplete_challenges');

//Route::resource('schools', SchoolController::class);
//Route::resource('representatives', RepresentativeController::class);







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

