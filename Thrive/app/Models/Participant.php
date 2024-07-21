<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Participant extends Model
{
    use HasFactory;
    protected $fillable = ['Username', 'Firstname', 'Lastname','EmailAddress','Date_of_Birth', 'regno', 'Image'];

    public function marks()
    {
        return $this->hasMany(Mark::class, 'Username', 'Username');
    }
}
