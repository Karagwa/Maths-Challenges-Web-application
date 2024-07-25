<!-- resources/views/challenges/index.blade.php -->
@extends('layouts.app', ['activePage' => 'questionanalytics', 'title' => 'Thrive Mathematical Challenges by Thrive challenges & Fantastic Five', 'navName' => 'Question Analytics', 'activeButton' => 'laravel'])

    <style>
        .container {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        table {
            width: 48%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        h2 {
            text-align: center;
        }
    </style>

@section('content') 
    <div class="content">
    <div class="container-fluid">
    <h3>Top and Bottom Correctly Answered Questions</h3>
    @foreach($topAndBottomQuestions as $challengename => $data)
        <h2>{{ $challengename }}</h2>
        <div class="container">
            <table>
                <thead>
                    <tr>
                        <th colspan="2">Top 3 Questions</th>
                    </tr>
                    <tr>
                        <th>Question</th>
                        <th>Average Score</th>
                    </tr>
                </thead>
                <tbody>
                    @foreach($data['top'] as $questionData)
                        <tr>
                            <td>{{ $questionData['question'] }}</td>
                            <td>{{ $questionData['average_score'] }}</td>
                        </tr>
                    @endforeach
                </tbody>
            </table>
            
            <table>
                <thead>
                    <tr>
                        <th colspan="2">Bottom 3 Questions</th>
                    </tr>
                    <tr>
                        <th>Question</th>
                        <th>Average Score</th>
                    </tr>
                </thead>
                <tbody>
                    @foreach($data['bottom'] as $questionData)
                        <tr>
                            <td>{{ $questionData['question'] }}</td>
                            <td>{{ $questionData['average_score'] }}</td>
                        </tr>
                    @endforeach
                </tbody>
            </table>
        </div>
    @endforeach
    
    </div>
    </div>
@endsection    
