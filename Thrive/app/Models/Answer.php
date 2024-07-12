<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Answer extends Model
{
    use HasFactory;
    protected $table='answers';

    protected $fillable = ['QuestionNo', 'Answer', 'ChallengeNo'];

    // public function question()
    // {
    //     return $this->belongsTo(Question::class);
    // }

    // public function challenge()
    // {
    //     return $this->belongsTo(Challenge::class);
    // }
}
