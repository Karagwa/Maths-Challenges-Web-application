<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class School extends Model
{
    use HasFactory;

    protected $table = 'schools';
    protected $fillable = ['id','name', 'district', 'regno'];

    public function representatives()
    {
        return $this->hasMany(Representative::class);
    }

    public function marks()
    {
        return $this->hasMany(Mark::class, 'regno', 'regno');
    }
}// app/Models/School.php







