@extends('layouts.app', ['activePage' => 'table', 'title' => 'Thrive Mathematical Challenges  by Thrive challenges & Fantastic Five', 'navName' => 'Table List', 'activeButton' => 'laravel'])
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title>CHALLENGE UPLOAD</title>
    <!-- Your other head elements -->
</head>
<body>
@section('content')
    <div class="content">
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-12">
                    <div class="card strpied-tabled-with-hover">
                        <div class="card-header ">
                            <h4 class="card-title">UPLOAD CHALLENGES</h4>
                            <p class="card-category">please enter the challenge information</p>
                        </div>
                        
                        <form method="POST" action="{{ route('challenges.store') }}" style="width: 800px; margin: 0 auto;">
                            @csrf
                            <br>
                            Challenge Number <input type="number" name="ChallengeNo" id="ChallengeNo" autocomplete="off">
                            <br><br>
                            Challenge Name <input type="text" name="ChallengeName" id="ChallengeName" autocomplete="name">
                            <br><br>
                            Opening Date <input type="date" name="OpeningDate" id="OpeningDate">
                            <br><br>
                            Closing Date <input type="date" name="ClosingDate" id="ClosingDate">
                            <br><br>
                            Challenge Duration (in minutes)<input type="number" name="ChallengeDuration" id="ChallengeDuration">
                            <br><br>
                            Number of questions<input type="number" name="NumberOfPresentedQuestions" id="NumberOfPresentedQuestions" autocomplete="off"> <br> 
                            <br><br>
                            <button type="submit">Upload Challenge Details</button>
 
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
                        <div class="card-header ">
                            <h4 class="card-title">UPLOAD QUESTIONS</h4>
                            <p class="card-category">please enter an excel file with the questions</p>
                        </div>
                        
                        <form method="POST"  action="{{ url('import') }}" style="width: 800px; margin: 0 auto;" enctype="multipart/form-data">
                            @csrf
                        <br>
                        <br>
                        <br>
                            Upload Questions File <input type="file" name="import_questions" autocomplete="off">
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
                        <div class="card-header ">
                            <h4 class="card-title">UPLOAD ANSWERS</h4>
                            <p class="card-category">please enter an excel file with answers</p>
                        </div>
                        
                        <form method="POST" action="{{ url('upload') }}"style="width: 800px; margin: 0 auto;" enctype="multipart/form-data">
                            @csrf
                        <br>
                            
                            
                            Upload Answers File <input type="file"  name="import_answers" autocomplete="off" required>
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
    function addChallenge() {
    const formData = new FormData(document.forms.sample);
    const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');
    fetch('/uploadChallenges', {
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': csrfToken
        },
        body: formData
    })
    .then(response => response.text())
    
    .then(data => {
        console.log(data);
    })
}
    </script>
</body>
</html>