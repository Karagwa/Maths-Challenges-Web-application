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
            //$table->id();
            //$table->timestamps();$table->string('Username');
            $table->string('Username');
            $table->primary('Username');
            $table->string('Firstname');
            $table->string('Lastname');
            $table->string('EmailAddress');
            $table->date('Date_of_Birth');
            $table->string('regno');
            $table->foreign('regno')->references('regno')->on('schools')->onDelete('cascade');
            //$table->string('image');
            $table->binary('Image'); // Store image as BLOB
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
        Schema::dropIfExists('participants');
    }
};
