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
        Schema::create('emails', function (Blueprint $table) {
          $table->string('EmailID',15)->primary();
          $table->time('Time');
          $table->string('Type',15);
          $table->string('Username',15);
          $table->string('RepresentativeID',15);
          
        $table->foreign('Username')->references('Username')->on('participants');
        $table->foreign('RepresentativeID')->references('RepresentativeID')->on('school_representatives');

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('emails');
    }
};
