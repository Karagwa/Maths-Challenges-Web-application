<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Mark extends Model
{
    use HasFactory;
    protected $fillable = ['Username','regno', 'ChallengeNumber', 'TotalScore','ChallengeCount'];
    public function participant()
    {
        return $this->belongsTo(Participant::class, 'Username', 'Username');
    }

    public function challenge()
    {
        return $this->belongsTo(Challenge::class, 'ChallengeNumber', 'ChallengeNumber');
    }

    public function school()
    {
        return $this->belongsTo(School::class, 'regno', 'regno');
    }
}
