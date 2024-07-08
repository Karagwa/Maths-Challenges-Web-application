<?php

namespace App\Http\Controllers\Auth;

use App\Http\Controllers\Controller;
use Illuminate\Foundation\Auth\AuthenticatesUsers;
use Illuminate\Http\Request;
use Illuminate\Spport\Facades\Auth;

class LoginController extends Controller
{
    /*
    |--------------------------------------------------------------------------
    | Login Controller
    |--------------------------------------------------------------------------
    |
    | This controller handles authenticating users for the application and
    | redirecting them to your home screen. The controller uses a trait
    | to conveniently provide its functionality to your applications.
    |
    */

    use AuthenticatesUsers;

    /**
     * Where to redirect users after login.
     *
     * @var string
     */
    protected $redirectTo = '/home';

    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('guest')->except('logout');
        $this->middleware('auth')->only('logout');
    }

    protected function credentials(Request $request){
        return [
            'email' => $request->email,
            'password' => $request->password,
            'is_active' => 1,
        ];
    }

    protected function authenticated(Request $request, $user){
        if ($user->is_admin) {
            return redirect()->route('admin.dashboard');
        }

        return redirect()->intended($this->redirectPath());
    }

    protected function validateLogin(Request $request){

        $request->validate([
            'email' => 'required|string|email',
            'password' => 'required|string',
        ],[
            'email.required' => 'The email field is required.',
            'email.email' => 'Please enter a valid email address.',
            'password.required' => 'The password field is required.',
        ]
    );
    }
}
