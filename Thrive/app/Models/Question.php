<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Question extends Model
{
    use HasFactory;
    protected $table='questions';

    protected $fillable = ['QuestionNo', 'Question', 'ChallengeNo'];

    // public function challenge()
    // {
    //     return $this->belongsTo(Challenge::class);
    // }

    // public function answers()
    // {
    //     return $this->belongsTo(Answer::class);
    // }
}
