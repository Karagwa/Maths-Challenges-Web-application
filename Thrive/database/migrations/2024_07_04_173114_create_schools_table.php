<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class  extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    
    public function up()
    {
        Schema::create('schools', function (Blueprint $table) {
            //$table->id();
            
            $table->string('name');
            $table->string('district');
            $table->string('regno')->unique();
            $table->primary('regno');
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
        Schema::dropIfExists('schools');
    }
};
// database/migrations/xxxx_xx_xx_xxxxxx_create_schools_table.php



