

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="{{ asset('css/light-bootstrap-dashboard.css') }}">
    <link rel="stylesheet" href="{{ asset('css/app.css') }}">

    <title>Sidebar Styling</title>
    <style>
        /* Sidebar CSS */
        .sidebar {
            
            padding: 20px;
            border-radius: 10px;
            color: white;
        }

        .sidebar .sidebar-wrapper {
            padding: 20px;
        }

        .sidebar .logo a {
            color: white;
            font-size: 1.5em;
            font-weight: bold;
            text-decoration: none;
            display: block;
            text-align: center;
            margin-bottom: 20px;
        }

        .sidebar .nav {
            list-style: none;
            padding-left: 0;
        }

        .sidebar .nav-item {
            margin-bottom: 10px;
        }

        .sidebar .nav-item a {
            color: white;
            text-decoration: none;
            padding: 10px 15px;
            display: flex;
            align-items: center;
            border-radius: 5px;
            transition: background 0.3s, color 0.3s;
        }

        .sidebar .nav-item a:hover,
        .sidebar .nav-item.active a {
            background: rgba(255, 255, 255, 0.2);
            color: white;
        }

        .sidebar .nav-item a i,
        .sidebar .nav-item a img {
            margin-right: 10px;
        }

        .sidebar .nav-item a p {
            margin: 0;
            font-size: 1em;
        }

        .sidebar .collapse {
            padding-left: 15px;
        }

        .sidebar .collapse .nav-item a {
            padding-left: 30px;
        }

    </style>
</head>


<body>
<div class="sidebar sidebar-gradient" data-color="" data-image="{{ asset('light-bootstrap/img/download.jpeg') }}">

    <!--
Tip 1: You can change the color of the sidebar using: data-color="purple | blue | green | orange | red"

Tip 2: you can also add an image using data-image tag
-->
    <div class="sidebar-wrapper">
        <div class="logo">
            <a href="http://www.creative-tim.com" class="simple-text">
                {{ __("Thrive Challenges") }}
            </a>
        </div>
        <ul class="nav">
            <li class="nav-item @if($activePage == 'dashboard') active @endif">
                <a class="nav-link" href="{{route('dashboard')}}">
                    <i class="nc-icon nc-chart-pie-35"></i>
                    <p>{{ __("Analysis") }}</p>
                </a>
            </li>
           
            <li class="nav-item @if($activePage == 'user') active @endif">
              <a class="nav-link" href="{{route('profile.edit')}}" >
                  <i class="nc-icon nc-single-02"></i>
                   <p>{{ __("User Profile") }}</p>
                </a>
                        
            </li>

            <li class="nav-item @if($activePage == 'table') active @endif">
                <a class="nav-link" href="{{route('page.index', 'table')}}">
                    <i class="nc-icon nc-notes"></i>
                    <p>{{ __("School registrations") }}</p>
                </a>
            </li>
            <li class="nav-item @if($activePage == 'table2') active @endif">
                <a class="nav-link" href="{{route('page.index', 'table2')}}">
                    <i class="nc-icon nc-notes"></i>
                    <p>{{ __("Representative registrations") }}</p>
                </a>
            </li>
            <li class="nav-item @if($activePage == 'typography') active @endif">
                <a class="nav-link" href="{{route('page.index', 'challenge')}}">
                    <i class="nc-icon nc-paper-2"></i>
                    <p>{{ __("Upload Challenges") }}</p>
                </a>
            </li>
            <li class="nav-item @if($activePage == 'rankings') active @endif">
                <a class="nav-link" href="{{route('rankings', 'rankings')}}">
                <i class="nc-icon nc-bullet-list-67"></i>
                    <p>{{ __("School Rankings") }}</p>
                </a>
            </li>
            <li class="nav-item @if($activePage == 'questionanalytics') active @endif">
                <a class="nav-link" href="{{route('questions', 'rankings')}}">
                <i class="nc-icon nc-atom"></i>
                    <p>{{ __("Question Analytics") }}</p>
                </a>
            </li>
          
            
           
        </ul>
    </div>
</div>
</body>
</html>
