@extends('layouts.app', ['activePage' => 'editing', 'title' => 'Thrive Mathematical Challenges by Thrive Challenges & Fantastic Five', 'navName' => 'Editing', 'activeButton' => 'laravel'])

@section('content')
<div class="card shadow-lg" style="margin: 20px;">
    <div class="card-header bg-primary text-white text-center">
        <h4 class="card-title">Edit the Field</h4>
    </div>
    <div class="card-body">
        <form action="{{ url('school/' . $school->id) }}" method="POST">
            @csrf
            @method('PATCH')
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" name="name" id="name" value="{{ $school->name }}" class="form-control" placeholder="Enter school name" />
            </div>
            <div class="form-group">
                <label for="district">District</label>
                <input type="text" name="district" id="district" value="{{ $school->district }}" class="form-control" placeholder="Enter district" />
            </div>
            <div class="form-group">
                <label for="regno">School Registration Number</label>
                <input type="text" name="regno" id="regno" value="{{ $school->regno }}" class="form-control" placeholder="Enter registration number" />
            </div>
            <button type="submit" class="btn btn-success btn-block">Update</button>
        </form>
    </div>
</div>
@endsection

@push('styles')
<style>
    body {
        background-color: #f8f9fa;
        font-family: 'Arial', sans-serif;
    }

    .card {
        border-radius: 10px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        border: none;
    }

    .card-header {
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        background-color: #007bff;
        color: #fff;
        padding: 15px;
        text-align: center;
    }

    .form-group label {
        font-weight: bold;
        color: #495057;
        margin-top: 10px;
    }

    .form-control {
        border-radius: 5px;
        box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        margin-bottom: 15px;
        padding: 10px;
        font-size: 16px;
    }

    .btn-success {
        background-color: #28a745;
        border-color: #28a745;
        transition: background-color 0.3s, border-color 0.3s;
        padding: 10px 20px;
        font-size: 18px;
    }

    .btn-success:hover {
        background-color: #218838;
        border-color: #1e7e34;
    }

    .text-center {
        text-align: center;
    }

    .text-white {
        color: #fff;
    }

    .btn-block {
        display: block;
        width: 100%;
    }

    .shadow-lg {
        box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
    }
</style>
@endpush