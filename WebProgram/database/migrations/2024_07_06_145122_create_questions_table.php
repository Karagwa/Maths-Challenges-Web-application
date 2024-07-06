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
        Schema::create('questions', function (Blueprint $table) {
            $table->integer('QuestionNumber');
            $table->string('ParticipantID',15);
            $table->integer('ChallengeNumber')->unsigned();

            $table->foreign('ParticipantID')->references('ParticipantID')->on('participants');
            $table->foreign('ChallengeNumber')->references('ChallengeNumber')->on('challenges');

            $table->primary(['QuestionNumber', 'ChallengeNumber']);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('questions');
    }
};
