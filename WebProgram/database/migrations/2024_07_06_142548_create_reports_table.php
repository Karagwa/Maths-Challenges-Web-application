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
        Schema::create('reports', function (Blueprint $table) {
           $table->string('ReportID',15)->primary();
            $table->integer('ChallengeNumber')->unsigned();
            $table->string('username', 15);
            $table->integer('Score');
            $table->time('Time_For_Each_Question');
            $table->time('Total_Time_For_Challenge');

            $table->foreign('ChallengeNumber')->references('ChallengeNumber')->on('challenges');
            $table->foreign('username')->references('username')->on('participants');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('reports');
    }
};
