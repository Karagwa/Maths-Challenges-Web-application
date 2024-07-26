@extends('layouts.app', ['activePage' => 'table', 'title' => 'Thrive Mathematical Challenges  by Thrive challenges & Fantastic Five', 'navName' => 'Challenges', 'activeButton' => 'laravel'])
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <link ref=Thrive\public\light-bootstrap\css\light-bootstrap-dashboard.css>
    <title>CHALLENGE UPLOAD</title>
    <style>
        #challengeForm {
            width: 800px;
            margin: 0 auto;
            text-align: center;
        }
        #challengeForm input[type="number"],
        #challengeForm input[type="text"],
        #challengeForm input[type="date"] {
            display: block;
            margin: 5px auto;
        }
        #ChallengeNumber, #ChallengeDuration, #NumberOfPresentedQuestions, #ChallengeName, #OpeningDate, #ClosingDate {
            width: 50%; 
        }
        
        
        button {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
        }
    </style>
    
</head>
<body>
@section('content')
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="card strpied-tabled-with-hover">
                        <div class="card-header ">
                            <h3 class="card-title" style="text-align:center; padding-top:50px">UPLOAD CHALLENGES</h3>
                            <p class="card-category" style="text-align:center">Please enter the challenge information</p>
                        </div>
                        
                        <form method="POST" action="{{ route('challenges.store') }}" style="width: 800px; margin: 0 auto" id= "challengeForm">
                            @csrf
                            <br>
                            Challenge Number <input type="number" name="ChallengeNumber" id="ChallengeNumber" autocomplete="off">
                            <br>
                            Challenge Name <input type="text" name="ChallengeName" id="ChallengeName" autocomplete="name">
                            <br>
                            Opening Date <input type="date" name="OpeningDate" id="OpeningDate">
                            <br>
                            Closing Date <input type="date" name="ClosingDate" id="ClosingDate">
                            <br>
                            Challenge Duration (in minutes)<input type="number" name="ChallengeDuration" id="ChallengeDuration">
                            <br>
                            Number of questions<input type="number" name="NumberOfPresentedQuestions" id="NumberOfPresentedQuestions" autocomplete="off">
                            <br>
                            <button type="submit" id="submitButton">Upload Challenge Details</button>
 
                           <div class="card-body table-full-width table-responsive">
                            <table class="table table-hover table-striped" id="tb1">
                                <thead>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Opening Date</th>
                                    <th>Closing Date</th>
                                    <th>Number of questions</th>
                                    <th>State</th>
                                    
                                </thead>
                                <tbody>
                          
                                </tbody>
                            </table>
                           </div>
                        </form>
                        
                    </div>
                </div>
               
            </div>
        </div>
    </div>

    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    @if (session('status'))
                        <div class="alert alert-success">
                            {{ session('status') }}
                        </div>
                    @endif
                    <div class="card strpied-tabled-with-hover">
                        <div class="card-header " style="text-align:center;">
                            <h3 class="card-title">UPLOAD QUESTIONS</h3>
                            <p class="card-category">Please enter an excel file with the questions</p>
                        </div>
                        <div class="form-container">
                            
                        <form method="POST"  action="{{ url('import') }}" style="width: 800px; margin: 0 auto;" enctype="multipart/form-data">
                            @csrf
                        <br>
                        
                            <b>Upload Questions File: </b><input type="file" name="import_questions" autocomplete="off">
                            <br>
                            <br>
                            
                            
                            
                           <button type="submit" >Upload Questions</button>
 
                           <div class="card-body table-full-width table-responsive">
                            <table class="table table-hover table-striped" id="tb1">
                                <thead>
                                    <th>Question Number</th>
                                    <th>Question</th>
                                    
                                </thead>
                                <tbody>
                          
                                </tbody>
                            </table>
                           </div>
                        </form>
                        
                    </div>
                </div>
               
            </div>
        </div>
    </div>

    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="card strpied-tabled-with-hover">
                        <div class="card-header" style="text-align:center;">
                            <h3 class="card-title">UPLOAD ANSWERS</h3>
                            <p class="card-category">Please enter an excel file with answers</p>
                        </div>
                        
                        <form method="POST" action="{{ url('upload') }}"style="width: 800px; margin: 0 auto;" enctype="multipart/form-data" style="text-align:center;">
                            @csrf
                        <br>
                            
                            
                            <b>Upload Answers File:</b> <input type="file"  name="import_answers" autocomplete="off" required >
                            <br>
                            <br>
                           <button type="submit">Upload Answers</button>
 
                           <div class="card-body table-full-width table-responsive">
                            <table class="table table-hover table-striped" id="tb1">
                                <thead>
                                    <th>Answer Number</th>
                                    <th>Answer</th>
                                    
                                    
                                </thead>
                                <tbody>
                          
                                </tbody>
                            </table>
                           </div>
                        </form>
                        
                    </div>
                </div>
               
            </div>
        </div>
    </div>
    
@endsection
<script>
document.addEventListener('DOMContentLoaded', function() {
    const submitButton = document.getElementById('submitButton');

  submitButton.addEventListener('click', function(event) {
    var openingDate = new Date(document.getElementById('OpeningDate').value);
    var closingDate = new Date(document.getElementById('ClosingDate').value);

    console.log('Opening Date:', openingDate);
    console.log('Closing Date:', closingDate);
    if (openingDate > closingDate) {
      alert('The opening date should be before the closing date.');
      event.preventDefault(); // Prevent form submission if validation fails
    }
  });

});

    </script>
</body>
</html>