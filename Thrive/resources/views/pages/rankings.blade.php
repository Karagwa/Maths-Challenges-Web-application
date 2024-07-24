@extends('layouts.app', ['activePage' => 'rankings', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Rankings', 'activeButton' => 'laravel'])

<title>Yearly Challenge Results</title>
    <style>
      
     
        table {
            width: 50%;
            border-collapse: collapse;
        }
        th, td {
            text-align: left;
            padding: 8px;
        }
        th {
            font-size: 1.2em;
        }
        h3, h4 {
            margin: 0 0 20px 0;
            padding: 0;
        }
    </style>
</head>
<body>



@section('content') 
    <div class="content">
    <div class="container-fluid">
    

    <h3 style="text-align: center; " >Yearly Challenge Results for {{ \Carbon\Carbon::now()->year }}</h3>
    
    

    <h4>School Rankings Across All Challenges</h4>
    <table>
        <thead>
            <tr>
                <th>School Name</th>
                <th>Average Marks</th>
            </tr>
        </thead>
        <tbody>
            @foreach($rankedSchools as $school)
                <tr>
                    <td>{{ $school->school->name }}</td>
                    <td>{{ $school->avg_marks }}</td>
                </tr>
            @endforeach
        </tbody>
    </table>


    <p>  </p>
    <h4>Top 10 Performing Schools Across All Challenges</h4>
    <table>
        <thead>
            <tr>
                <th>School Name</th>
                <th>Average Marks</th>
            </tr>
        </thead>
        <tbody>
            @foreach($top10Schools as $school)
                <tr>
                    <td>{{ $school->school->name }}</td>
                    <td>{{ $school->avg_marks }}</td>
                </tr>
            @endforeach
        </tbody>
    </table>

    <p>  </p>
    <h4>Bottom 10 Performing Schools Across All Challenges</h4>
    <table>
        <thead>
            <tr>
                <th>School Name</th>
                <th>Average Marks</th>
            </tr>
        </thead>
        <tbody>
            @foreach($bottom10Schools as $school)
                <tr>
                    <td>{{ $school->school->name }}</td>
                    <td>{{ $school->avg_marks }}</td>
                </tr>
            @endforeach
        </tbody>
    </table>

    @foreach ($worstSchoolsPerChallenge as $challenge)
        <h4>Bottom 5 Schools for Challenge: {{ $challenge->ChallengeName }} (Ended on: {{ $challenge->ClosingDate }})</h4>
        @if($challenge->marks->isEmpty())
            <p>No schools have participated in this challenge.</p>
        @else
            <table>
                <thead>
                    <tr>
                        <th>School Name</th>
                        <th>Average Marks</th>
                    </tr>
                </thead>
                <tbody>
                    @foreach ($challenge->marks as $ranking)
                        <tr>
                            <td>{{ $ranking->school->name }}</td>
                            <td>{{ $ranking->avg_marks }}</td>
                        </tr>
                    @endforeach
                </tbody>
            </table>
        @endif
    @endforeach
    </div>
    </div>
@endsection