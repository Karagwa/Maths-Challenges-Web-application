<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Challenge extends Model
{
    use HasFactory;
    protected $fillable = ['ChallengeNumber', 'ChallengeName', 'OpeningDate', 'ClosingDate', 'ChallengeDuration', 'NumberOfPresentedQuestions'];
    
    public function questions()
    {
        return $this->hasMany(Question::class);
    }

    public function marks()
    {
        return $this->hasMany(Mark::class, 'ChallengeNumber', 'ChallengeNumber');
    }

    public function questionScores()
    {
        return $this->hasMany(QuestionScore::class, 'challengenumber', 'challengenumber');
    }

    
}
