@extends('layouts/app', ['activePage' => 'welcome', 'title' => 'Thrive mathematical challenge by group J'])

@section('content')
    <div class="full-page section-image" data-color="  237, 231, 246" data-image="{{asset('light-bootstrap/img/child.jpeg')}}">
        <div class="content">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-7 col-md-8">
                        <h1 class="text-white text-center" style="width: 800px; text-align:center;" >{{ __('Welcome to Thrive Mathematics Competition. This page is for only Administrators ') }}</h1>
                    </div>
                </div>
            </div>
        </div>
    </div>
@endsection

@push('js')
    <script>
        $(document).ready(function() {
            demo.checkFullPageBackgroundImage();

            setTimeout(function() {
                // after 1000 ms we add the class animated to the login/register card
                $('.card').removeClass('card-hidden');
            }, 700)
        });
    </script>
@endpush