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
        Schema::create('participants', function (Blueprint $table) {
            $table->string('Username', 15)->primary();
            $table->string('FirstName', 20);
            $table->string('LastName', 20);
            $table->string('EmailAddress', 30)->unique();
            $table->date('DateOfBirth', 10);
            $table->string('ParticipantID', 10)->unique();
            $table->string('School_Registration_Number', 15);
            $table->integer('PortNo');
            $table->binary('Image');

            $table->foreign('PortNo')->references('PortNo')->on('java_server');
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
        Schema::dropIfExists('participants');
    }
};
