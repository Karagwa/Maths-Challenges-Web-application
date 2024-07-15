<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class School extends Model
{
    use HasFactory;

    
    protected $fillable = ['username','name', 'district', 'regno'];

    public function representatives()
    {
        return $this->hasMany(Representative::class);
    }
}// app/Models/School.php





