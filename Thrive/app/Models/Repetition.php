<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Repetition extends Model
{
    use HasFactory;
    protected $fillable = [
        'challenge_number',
        'repetition_percentage',
    ];
}
