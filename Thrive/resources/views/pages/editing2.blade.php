@extends('layouts.app', ['activePage' => 'editing2', 'title' => 'Thrive Mathematical Challenges by Thrive Challenges & Fantastic Five', 'navName' => 'Editing2', 'activeButton' => 'laravel'])

@section('content')
<div class="card shadow-lg" style="margin: 20px;">
    <div class="card-header bg-primary text-white">
        <h4 class="card-title">Edit the Field</h4>
    </div>
    <div class="card-body">
        <form action="{{ url('representative/' . $representative->id) }}" method="POST">
            @csrf
            @method('PATCH')
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" name="username" id="username" value="{{ $representative->username }}" class="form-control" placeholder="Enter school name" />
            </div>
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" name="name2" id="name" value="{{ $representative->name }}" class="form-control" placeholder="Enter representative name" />
            </div>
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" name="email" id="email" value="{{ $representative->email }}" class="form-control" placeholder="Enter email" />
            </div>
            <div class="form-group">
                <label for="regno">School Registration Number</label>
                <input type="text" name="regno" id="regno" value="{{ $representative->regno }}" class="form-control" placeholder="Enter registration number" />
            </div>
            <div class="form-group">
                <label for="name">Password</label>
                <input type="password" name="password" id="password" value="{{ $representative->email }}" class="form-control" placeholder="Enter the password" />
            </div>
            <button type="submit" class="btn btn-success btn-block">Update</button>
        </form>
    </div>
</div>
@endsection

<!-- Custom CSS -->
<style>
    .card {
        border-radius: 10px;
    }

    .card-header {
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
    }

    .form-group label {
        font-weight: bold;
        color: #333;
    }

    .form-control {
        border-radius: 5px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
    }

    .btn-success {
        background-color: #28a745;
        border-color: #28a745;
        transition: background-color 0.3s, border-color 0.3s;
    }

    .btn-success:hover {
        background-color: #218838;
        border-color: #1e7e34;
    }
</style>
