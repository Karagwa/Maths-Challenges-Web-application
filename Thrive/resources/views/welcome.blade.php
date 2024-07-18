@extends('layouts.app', ['activePage' => 'welcome', 'title' => 'Thrive mathematical challenge by group J'])

@section('content')
    <style>
        body, html {
            height: 100%;
            margin: 0;
           
        }

        h1 {
            font-size: 2.5rem;
            margin-bottom: 20px;
            color: white;
            animation: flashIn 0s ease-in-out;
            text-align: center;
        }
        

        @keyframes flashIn {
            from {
                opacity: 0;
                transform: scale(0.8);
            }
            to {
                opacity: 1;
                transform: scale(1);
            }
        }

        .carousel,
        .carousel-inner,
        .carousel-item {
            height: 100%;
        }

        .carousel-item {
            background-size: cover;
            background-position: center;
        }

        .carousel-caption {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            color: white;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.8);
        }

        .navbar {
            z-index: 10;
            position: fixed;
            width: 100%;
        }

        .carousel-item img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
    </style>

    <div id="carouselExampleIndicators" class="carousel slide full-page section-image" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="{{ asset('light-bootstrap/img/image3.jpeg') }}" alt="First slide">
                <div class="carousel-caption d-none d-md-block">
                    <h1>{{ __('Welcome to Thrive Mathematics Competition. This page is for only Administrators') }}</h1>
                </div>
            </div>
            <div class="carousel-item">
                <img src="{{ asset('light-bootstrap/img/image1.jpeg') }}" alt="Second slide">
                <div class="carousel-caption d-none d-md-block">
                    <h1>{{ __('Thank you for your dedication. Let’s work together to create an outstanding learning environment for our students.') }}</h1>
                </div>
            </div>
            <div class="carousel-item">
                <img src="{{ asset('light-bootstrap/img/image2.jpeg') }}" alt="Third slide">
                <div class="carousel-caption d-none d-md-block">
                    <h1>{{ __(' Welcome to the admin portal. Your expertise is invaluable to us, and we’re excited to work together to enhance the student experience.') }}</h1>
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
          
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
        
            <span class="sr-only">Next</span>
        </a>
    </div>
@endsection

@push('js')
    <script>
        $(document).ready(function() {
            demo.checkFullPageBackgroundImage();

            setTimeout(function() {
                // after 1000 ms we add the class animated to the login/register card
                $('.card').removeClass('card-hidden');
            }, 700);

            // Initialize carousel with auto-slide
            $('.carousel').carousel({
                interval: 5000 // changes the speed
            });
        });
    </script>
@endpush
