@extends('layouts.app', ['activePage' => 'rankings', 'title' => 'Light Bootstrap Dashboard Laravel by Creative Tim & UPDIVISION', 'navName' => 'Rankings', 'activeButton' => 'laravel'])

<title>Yearly Challenge Results</title>
    <style>
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            text-align: left;
            padding: 12px;
            border: 1px solid #dee2e6;
        }
        th {
            /*font-size: 1.2em;*/
            background-color: #e9ecef;
        }
        h4 {
            text-align: center;
            color: #495057;
            margin-top: 20px;
        }
        .rankings {
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
        }
        .ranking-table {
            width: 45%;
            margin: 0 2.5%;
            border: 1px solid #dee2e6;
            border-radius: 10px;
            padding: 20px;
            background-color: #ffffff;
        }
        .ranking-table h6 {
            text-align: center;
            margin-bottom: 10px;
            
        }
        .ranking-table h6.bottom {
            color: #dc3545;
        }
    </style>
</head>
<body>



@section('content') 
    <h4 style="text-decoration: underline;">Yearly Challenge Results for {{ \Carbon\Carbon::now()->year }}</h4>

    <div class="rankings">
        <div class="ranking-table">
            <h6 style="color: green;">Top 10 Performing Schools Across All Challenges</h6>
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
        </div>

        <div class="ranking-table">
            <h6 style="color: #dc3545;">Bottom 10 Performing Schools Across All Challenges</h6>
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
        </div>
    </div>

    <h4 >School Rankings Across All Challenges</h4>
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
@endsection