<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('school_representatives', function (Blueprint $table) {
            $table->string('RepresentativeID',15)->primary();
            $table->string('School_Registration_Number',15);
            $table->string('Email_of_Representative',30);
            $table->string('Name_of_representative',30);
            $table->string('Username',15);
            $table->integer('PortNo');

            
            $table->foreign('School_Registration_Number')->references('School_Registration_Number')->on('schools');
            $table->foreign('PortNo')->references('PortNo')->on('java_server');
            $table->foreign('Username')->references('Username')->on('participants');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('school_representatives');
    }
};
