<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Representative extends Model
{
    use HasFactory;

    protected $fillable = ['id','username','name', 'email','regno','password'];

    public function school()
    {
        return $this->belongsTo(School::class);
    }
}
