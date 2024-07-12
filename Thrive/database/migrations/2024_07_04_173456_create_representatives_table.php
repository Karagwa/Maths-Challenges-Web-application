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
        Schema::create('representatives', function (Blueprint $table) {
        
            $table->string('UserName');
            $table->string('EmailOfRepresentative')->primary();
            $table->string('NameOfRepresentative');
            $table->string('regno')->references('regno')->on('schools');
            
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


