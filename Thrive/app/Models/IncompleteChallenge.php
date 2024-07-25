<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class IncompleteChallenge extends Model
{
    protected $table = 'incomplete_challenges';
    use HasFactory;
    protected $fillable = ['username','Firstname','Lastname','ChallengeNumber'];
}
