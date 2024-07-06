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
        Schema::create('analysis_reports', function (Blueprint $table) {
            $table->string('Analysis_Report_ID', 15)->primary();
            $table->integer('PortNo');
            $table->integer('Most_Attempted_Question');
            $table->integer('School_Ranking');
            $table->integer('Performance_over_years');
            $table->integer('Percentage_completion');

            $table->foreign('PortNo')->references('PortNo')->on('java_server')
            ->onDelete('cascade') // Optional: defines action on delete
            ->onUpdate('cascade'); // Optional: defines action on update
        });


    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('analysis_reports');
    }
};
