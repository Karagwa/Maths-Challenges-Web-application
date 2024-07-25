<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Question extends Model
{
    use HasFactory;
    protected $table='questions';

    protected $fillable = ['QuestionNo', 'Question', 'ChallengeNumber'];

    public function questionScores()
    {
        return $this->hasMany(QuestionScore::class, 'QuestionNo', 'QuestionNo');
    }

    public function challenge()
    {
        return $this->belongsTo(Challenge::class, 'ChallengeNumber', 'ChallengeNumber');
    }
}
