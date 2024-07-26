<!DOCTYPE html>
<html>
<head>
    <title>Best Pupils</title>
    <style>

        body {
            font-family: Arial, sans-serif;
            background-color:  	#D3D3D3;
            
        }

        .img1{
            width: 600px; 
            border: none;
            padding-left: 350px;
        }

        h1{
            text-align: center;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            font-size: 18px;
            text-align: left;
        }

        th, td {
            padding: 12px;
            border: 2px solid black;
            background-color: white;
        }

        th {
            background-color: white;
            text-align: center;
            font-weight: bold;
        }

        tr:hover {
            background-color: rgb(240, 238, 234);
       }
    </style>   
</head>
<body>
    <h1>Best Pupils per Challenge</h1>
    <img src="{{ asset('images/congs7.png') }}" class="img1"  >
    @if($challenges->isEmpty())
        <p>No challenges have ended yet.</p>
    @else
        @foreach ($challenges as $challengeId => $participants)
            <h2 >Challenge: {{ $participants->first()['challenge_name'] }} (Ended on: {{ $participants->first()['challenge_end_date'] }})</h2>
            <table>
                <thead>
                    <tr>
                        <th>Image</th>
                        <th>Name</th>
                        <th>Marks</th>
                        <th>School</th>
                    </tr>
                </thead>
                <tbody>
                    @foreach ($participants as $participant)
                        <tr>
                            <td><img src="data:image/jpeg;base64,{{ $participant['image'] }}" alt="{{ $participant['name'] }}'s image" width="100"></td>
                            <td>{{ $participant['name'] }}</td>
                            <td>{{ $participant['marks'] }}</td>
                            <td>{{ $participant['school'] }}</td>
                        </tr>
                    @endforeach
                </tbody>
            </table>
        @endforeach
    @endif
</body>
</html>
