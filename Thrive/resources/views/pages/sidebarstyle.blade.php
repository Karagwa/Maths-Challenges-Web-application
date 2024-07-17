<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sidebar Styling</title>
    <style>
      /* Custom CSS for the main sidebar */
.main-sidebar {
    background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%) !important;
    padding: 20px;
    border-radius: 10px;
    color: white;
}

.main-sidebar .sidebar-wrapper {
    padding: 20px;
}

.main-sidebar .logo a {
    color: white;
    font-size: 1.5em;
    font-weight: bold;
    text-decoration: none;
    display: block;
    text-align: center;
    margin-bottom: 20px;
}

.main-sidebar .nav {
    list-style: none;
    padding-left: 0;
}

.main-sidebar .nav-item {
    margin-bottom: 10px;
}

.main-sidebar .nav-item a {
    color: white;
    text-decoration: none;
    padding: 10px 15px;
    display: flex;
    align-items: center;
    border-radius: 5px;
    transition: all 150ms ease-in;
}

.main-sidebar .nav-item a:hover,
.main-sidebar .nav-item.active a {
    background: rgba(255, 255, 255, 0.2);
    color: white;
}

.main-sidebar .nav-item a i,
.main-sidebar .nav-item a img {
    margin-right: 10px;
}

.main-sidebar .nav-item a p {
    margin: 0;
    font-size: 1em;
}

.main-sidebar .collapse {
    padding-left: 15px;
}

.main-sidebar .collapse .nav-item a {
    padding-left: 30px;
}

.main-sidebar .nav-link.active.bg-danger {
    background: linear-gradient(135deg, #ff416c 0%, #ff4b2b 100%) !important;
    color: white;
}

.main-sidebar .nav-link.active.bg-danger:hover {
    background: linear-gradient(135deg, #ff4b2b 0%, #ff416c 100%) !important;
}

    </style>
</head>

<body>
<div class="fixed-plugin">
    <div class="dropdown show-dropdown">
        <a href="#" data-toggle="dropdown">
            <i class="fa fa-cog fa-2x"> </i>
        </a>
        <ul class="dropdown-menu">
            <li class="header-title"> {{ __('Sidebar Style') }}</li>
            <li class="adjustments-line">
                <a href="javascript:void(0)" class="switch-trigger">
                    <p>{{ __('Background Image') }}</p>
                    <label class="switch">
                        <input type="checkbox" data-toggle="switch" checked="" data-on-color="primary" data-off-color="primary">
                        <span class="toggle"></span>
                    </label>
                    <div class="clearfix"></div>
                </a>
            </li>
            <li class="adjustments-line">
                <a href="javascript:void(0)" class="switch-trigger background-color">
                    <p>Filters</p>
                    <div class="pull-right">
                        <span class="badge filter badge-black" data-color="black"></span>
                        <span class="badge filter badge-azure" data-color="azure"></span>
                        <span class="badge filter badge-green" data-color="green"></span>
                        <span class="badge filter badge-orange" data-color="orange"></span>
                        <span class="badge filter badge-red" data-color="red"></span>
                        <span class="badge filter badge-purple active" data-color="purple"></span>
                    </div>
                    <div class="clearfix"></div>
                </a>
            </li>
            <li class="header-title">{{ __('Sidebar Images') }}</li>
            <li class="active">
                <a class="img-holder switch-trigger" href="javascript:void(0)">
                    <img src="{{ asset('/light-bootstrap/img/sidebar-1.jpg') }}" alt="" />
                </a>
            </li>
            <li>
                <a class="img-holder switch-trigger" href="javascript:void(0)">
                    <img src="{{ asset('light-bootstrap/img/sidebar-3.jpg') }}" alt="" />
                </a>
            </li>
            <li>
                <a class="img-holder switch-trigger" href="javascript:void(0)">
                    <img src="{{ asset('light-bootstrap/img/sidebar-4.jpg') }}" alt="" />
                </a>
            </li>
            <li>
                <a class="img-holder switch-trigger" href="javascript:void(0)">
                    <img src="{{ asset('light-bootstrap/img/sidebar-5.jpg') }}" alt="" />
                </a>
            </li>
            <li class="button-container">
                <div class="">
                    <a href="https://www.creative-tim.com/product/light-bootstrap-dashboard-laravel" target="_blank" class="btn btn-info btn-block btn-fill">{{ __("Download, it's free!") }}</a>
                </div>
            </li>
            <li class="button-container">
                <div class="">
                    <a href="https://light-bootstrap-dashboard-laravel.creative-tim.com/docs/tutorial-components.html" target="_blank" class="btn btn-default btn-block btn-fill">{{ __('View Documentation') }}</a>
                </div>
            </li>
            <li class="header-title pro-title text-center">{{ __('Want more components?') }}</li>
            <li class="button-container">
                <div class="">
                    <a href="https://www.creative-tim.com/product/light-bootstrap-dashboard-pro-laravel" target="_blank" class="btn btn-warning btn-block btn-fill">{{ __('Get The PRO Version!') }}</a>
                </div>
            </li>
            <li class="header-title" id="sharrreTitle">{{ __('Thank you for sharing!') }}</li>
            <li class="button-container">
                <button id="twitter" class="btn btn-social btn-outline btn-twitter btn-round sharrre twitter-sharrre"><i class="fa fa-twitter"></i>{{ __('· 256') }}</button>
                <button id="facebook" class="btn btn-social btn-outline btn-facebook btn-round sharrre facebook-sharrre"><i class="fa fa-facebook-square"></i>{{ __('· 426') }}</button>
            </li>
        </ul>
    </div>
</div>
</body>