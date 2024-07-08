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
        Schema::create('rejecteds', function (Blueprint $table) {
            $table->string('Username', 15)->primary();
            $table->string('FirstName', 15);
            $table->string('LastName', 15);
            $table->string('EmailAddress', 30)->unique();
            $table->date('DateOfBirth');
            $table->string('RejectedID', 15)->unique();
            $table->string('School_Registration_Number', 15);
            
            $table->binary('Image');

            
            $table->foreign('School_Registration_Number')->references('School_Registration_Number')->on('schools');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('rejecteds');
    }
};
