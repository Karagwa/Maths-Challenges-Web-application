<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class QuestionScore extends Model
{
    use HasFactory;
    protected $table = 'questionscores'; // tablename
    protected $primaryKey = 'ChallengeNumber';

    protected $fillable = ['ChallengeNumber', 'QuestionNo', 'questionScore'];

    public function question()
    {
        return $this->belongsTo(Question::class, 'QuestionNo', 'QuestionNo');
    }

    public function challenge()
    {
        return $this->belongsTo(Challenge::class, 'ChallengeNumber', 'ChallengeNumber');
    }
}
