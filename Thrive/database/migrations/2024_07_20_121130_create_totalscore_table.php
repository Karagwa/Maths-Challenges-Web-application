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
        Schema::create('totalscore', function (Blueprint $table) {
            //$table->id();
            //$table->timestamps();
            $table->string('Username');
            $table->foreign('Username')->references('Username')->on('participant')->onDelete('cascade');
            $table->string('regno');
            $table->foreign('regno')->references('regno')->on('schools')->onDelete('cascade');
            $table->integer('ChallengeNumber');
            $table->foreign('ChallengeNumber')->references('ChallengeNumber')->on('challenges')->onDelete('cascade');
            $table->integer('TotalScore');
            $table->integer('ChallengeCount');
            
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('totalscore');
    }
};
