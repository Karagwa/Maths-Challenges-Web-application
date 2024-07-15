<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;
use Illuminate\Validation\Rules\Unique;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('representatives', function (Blueprint $table) {
            //$table->id();
            $table->string('username');
            $table->string('name');
            $table->string('email');
            $table->primary('email');
            $table->string('regno');
            $table->string('password');
            $table->foreign('regno')->references('regno')->on('schools')->onDelete('cascade');
            $table->timestamps();
            
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('representatives');
    }
};
// database/migrations/xxxx_xx_xx_xxxxxx_create_representatives_table.php


