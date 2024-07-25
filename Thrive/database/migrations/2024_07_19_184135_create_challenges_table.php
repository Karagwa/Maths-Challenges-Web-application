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
        Schema::create('challenges', function (Blueprint $table) {
            $table->integer('ChallengeNumber')->primary();
            $table->string('ChallengeName',15);
            $table->date('OpeningDate');
            $table->date('ClosingDate');
            $table->integer('ChallengeDuration');
            $table->integer('NumberOfPresentedQuestions');

        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('challenges');
    }
};
